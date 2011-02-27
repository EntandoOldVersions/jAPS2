/*
*
* Copyright 2005 AgileTec s.r.l. (http://www.agiletec.it) All rights reserved.
*
* This file is part of jAPS software.
* jAPS is a free software; 
* you can redistribute it and/or modify it
* under the terms of the GNU General Public License (GPL) as published by the Free Software Foundation; version 2.
* 
* See the file License for the specific language governing permissions   
* and limitations under the License
* 
* 
* 
* Copyright 2005 AgileTec s.r.l. (http://www.agiletec.it) All rights reserved.
*
*/
package com.agiletec.plugins.jacms.aps.system.services.content;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.agiletec.aps.system.common.entity.AbstractEntitySearcherDAO;
import com.agiletec.aps.system.common.entity.model.ApsEntityRecord;
import com.agiletec.aps.system.common.entity.model.EntitySearchFilter;
import com.agiletec.aps.system.services.group.Group;
import com.agiletec.aps.util.DateConverter;
import com.agiletec.plugins.jacms.aps.system.services.content.model.ContentRecordVO;

/**
 * Data access object used to search and load the contents.
 * @author E.Santoboni
 * @deprecated From jAPS 2.0 version 2.0.9. Use {@link PublicContentSearcherDAO}
 */
public class ContentSearcherDAO extends AbstractEntitySearcherDAO implements IContentSearcherDAO {
	
	@Override
	public List<String> loadContentsId(String[] categories, EntitySearchFilter[] filters, 
			Collection<String> userGroupCodes, boolean onlyOwner) {
		List<String> contentsId = new ArrayList<String>();
		if (userGroupCodes == null || userGroupCodes.size()==0) {
			return contentsId;
		}
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet result = null;
		try {
			conn = this.getConnection();
			stat = this.buildStatement(filters, categories, userGroupCodes, onlyOwner, false, conn);
			result = stat.executeQuery();
			this.flowResult(contentsId, filters, result);
		} catch (Throwable t) {
			processDaoException(t, "Errore in caricamento lista id contenuti", "loadContentsId");
		} finally {
			closeDaoResources(result, stat, conn);
		}
		return contentsId;
	}
	
	@Override
	public List<String> loadPublicContentsId(String[] categories,
			EntitySearchFilter[] filters, Collection<String> userGroupCodes) {
		Set<String> groupCodes = new HashSet<String>();
		if (null != userGroupCodes) groupCodes.addAll(userGroupCodes);
		groupCodes.add(Group.FREE_GROUP_NAME);
		EntitySearchFilter onLineFilter = new EntitySearchFilter(IContentManager.CONTENT_ONLINE_FILTER_KEY, false);
		filters = this.addFilter(filters, onLineFilter);
		return this.loadContentsId(categories, filters, groupCodes, false);
	}
	
	@Override
	public List<String> loadPublicContentsId(String contentType, String[] categories,
			EntitySearchFilter[] filters, Collection<String> userGroupCodes) {
		Set<String> groupCodes = new HashSet<String>();
		if (null != userGroupCodes) groupCodes.addAll(userGroupCodes);
		groupCodes.add(Group.FREE_GROUP_NAME);
		EntitySearchFilter onLineFilter = new EntitySearchFilter(IContentManager.CONTENT_ONLINE_FILTER_KEY, false);
		filters = this.addFilter(filters, onLineFilter);
		if (contentType != null && contentType.trim().length()>0) {
			EntitySearchFilter typeFilter = new EntitySearchFilter(IContentManager.ENTITY_TYPE_CODE_FILTER_KEY, false, contentType, false);
			filters = this.addFilter(filters, typeFilter);
		}
		return this.loadContentsId(categories, filters, groupCodes, false);
	}
	
	private PreparedStatement buildStatement(EntitySearchFilter[] filters, String[] categories, 
			Collection<String> userGroupCodes, boolean onlyOwner, boolean selectAll, Connection conn) {
		Collection<String> groupsForSelect = this.getGroupsForSelect(userGroupCodes, onlyOwner);
		String query = this.createQueryString(filters, categories, groupsForSelect, onlyOwner, selectAll);
		//System.out.println("QUERY : " + query);
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(query);
			int index = 0;
			index = super.addAttributeFilterStatementBlock(filters, index, stat);
			index = this.addMetadataFieldFilterStatementBlock(filters, index, stat);
			if (groupsForSelect != null) {
				index = this.addGroupStatementBlock(groupsForSelect, index, stat);
				if (!onlyOwner) {
					index = this.addGroupStatementBlock(groupsForSelect, index, stat);
				}
			}
			if (categories != null) {
				for (int i=0; i<categories.length; i++) {
					stat.setString(++index, categories[i]);
				}
			}
		} catch (Throwable t) {
			processDaoException(t, "Errore in fase di creazione statement", "buildStatement");
		}
		return stat;
	}
	
	protected int addGroupStatementBlock(Collection<String> groupCodes, int index, PreparedStatement stat) throws Throwable {
		Iterator<String> groupIter = groupCodes.iterator();
		while (groupIter.hasNext()) {
			String groupName = groupIter.next();
			stat.setString(++index, groupName);
		}
		return index;
	}
	
	private String createQueryString(EntitySearchFilter[] filters, String[] categories, 
			Collection<String> groupsForSelect, boolean onlyOwner, boolean selectAll) {
		StringBuffer query = this.createBaseQueryBlock(filters, selectAll);
		
		boolean hasAppendWhereClause = this.appendFullAttributeFilterQueryBlocks(filters, query, false);
		hasAppendWhereClause = this.appendMetadataFieldFilterQueryBlocks(filters, query, hasAppendWhereClause);
		
		if (groupsForSelect != null && !groupsForSelect.isEmpty()) {
			hasAppendWhereClause = this.verifyWhereClauseAppend(query, hasAppendWhereClause);
			this.addGroupsQueryBlock(query, groupsForSelect, onlyOwner);
		}
		if (categories != null) {
			hasAppendWhereClause = this.verifyWhereClauseAppend(query, hasAppendWhereClause);
			for (int i=0; i<categories.length; i++) {
				if (i>0) query.append(" AND ");
				query.append(" contents.contentid IN (SELECT contentid FROM ")
					.append(this.getContentRelationsTableName()).append(" WHERE ")
					.append(this.getContentRelationsTableName()).append(".refcategory = ? ) ");
			}
		}
		
		boolean ordered = appendOrderQueryBlocks(filters, query, false);
		//System.out.println("********** " + query.toString());
		return query.toString();
	}
	
	protected void addGroupsQueryBlock(StringBuffer query,
			Collection<String> userGroupCodes, boolean onlyOwner) {
		query.append(" ( ");
		int size = userGroupCodes.size();
		for (int i=0; i<size; i++) {
			if (i!=0) query.append("OR ");
			query.append("contents.maingroup = ? ");
		}
		if (!onlyOwner) {
			query.append(" OR contents.contentid IN ( SELECT contentid FROM ")
					.append(this.getContentRelationsTableName()).append(" WHERE ");
			for (int i=0; i<size; i++) {
				if (i!=0) query.append("OR ");
				query.append(this.getContentRelationsTableName()).append(".refgroup = ? ");
			}
			query.append(") ");
		}
		query.append(") ");
	}
	
	@Override
	public String getTableFieldName(String metadataFieldKey) {
		if (metadataFieldKey.equals(IContentManager.ENTITY_ID_FILTER_KEY)) {
			return this.getEntityMasterTableIdFieldName();
		} else if (metadataFieldKey.equals(IContentManager.ENTITY_TYPE_CODE_FILTER_KEY)) {
			return this.getEntityMasterTableIdTypeFieldName();
		} else if (metadataFieldKey.equals(IContentManager.CONTENT_DESCR_FILTER_KEY)) {
			return "descr";
		} else if (metadataFieldKey.equals(IContentManager.CONTENT_STATUS_FILTER_KEY)) {
			return "status";
		} else if (metadataFieldKey.equals(IContentManager.CONTENT_CREATION_DATE_FILTER_KEY)) {
			return "created";
		} else if (metadataFieldKey.equals(IContentManager.CONTENT_MODIFY_DATE_FILTER_KEY)) {
			return "lastmodified";
		} else if (metadataFieldKey.equals(IContentManager.CONTENT_ONLINE_FILTER_KEY)) {
			return "onlinexml";
		} else throw new RuntimeException("Chiave di ricerca '" + metadataFieldKey + "' non riconosciuta");
	}
	
	protected Collection<String> getGroupsForSelect(Collection<String> userGroupCodes, boolean onlyOwner) {
		if (userGroupCodes != null && userGroupCodes.contains(Group.ADMINS_GROUP_NAME)) {
			return null;
		} else {
			Collection<String> groupsForSelect = new HashSet<String>();
			if (userGroupCodes != null) groupsForSelect.addAll(userGroupCodes);
			return groupsForSelect;
		}
	}
	
	@Override
	protected ApsEntityRecord createRecord(ResultSet result) throws Throwable {
		ContentRecordVO contentVo = new ContentRecordVO();
		contentVo.setId(result.getString("contentid"));
		contentVo.setTypeCode(result.getString("contenttype"));
		contentVo.setDescr(result.getString("descr"));
		contentVo.setStatus(result.getString("status"));
		String xmlWork = result.getString("workxml");
		contentVo.setCreate(DateConverter.parseDate(result.getString("created"), this.DATE_FORMAT));
		contentVo.setModify(DateConverter.parseDate(result.getString("lastmodified"), this.DATE_FORMAT));
		String xmlOnLine = result.getString("onlinexml");
		contentVo.setOnLine(null != xmlOnLine && xmlOnLine.length() > 0);
		contentVo.setSync(xmlWork.equals(xmlOnLine));
		String mainGroupCode = result.getString("maingroup");
		contentVo.setMainGroupCode(mainGroupCode);
		contentVo.setXmlWork(xmlWork);
		contentVo.setXmlOnLine(xmlOnLine);
		return contentVo;
	}
	
	@Override
	public String getEntityMasterTableName() {
		return "contents";
	}
	@Override
	public String getEntityMasterTableIdFieldName() {
		return "contentid";
	}
	@Override
	public String getEntityMasterTableIdTypeFieldName() {
		return "contenttype";
	}
	@Override
	public String getEntitySearchTableName() {
		return "contentsearch";
	}
	@Override
	public String getEntitySearchTableIdFieldName() {
		return "contentid";
	}
	public String getContentRelationsTableName() {
		return "contentrelations";
	}
	
	protected final String DATE_FORMAT = "yyyyMMddHHmmss";
	
}
