package dxat.appserver.manager.database;


import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import dxat.appserver.manager.exceptions.OrgNotFoundException;
import dxat.appserver.manager.exceptions.UserNotFoundException;
import dxat.appserver.manager.pojos.TOrg;
import dxat.appserver.manager.pojos.OrgUser;

public class Update {
	private static final String OCOLLECTION   = "ORG_COLLECTION";
	
	DBAccess dbaccess = new DBAccess();
	DB db = dbaccess.setDb();
	
	public void updateOrg(TOrg torg) throws OrgNotFoundException {

		DBCollection collection = db.getCollection(OCOLLECTION);
		if (existsOrg(collection, torg.getIdentifier())) {

			BasicDBObject updateOrg = new BasicDBObject("identifier",
					torg.getIdentifier());
			DBObject org = collection.findOne(updateOrg);
			DBObject userdoc = new BasicDBObject();

			userdoc.put("identifier", torg.getIdentifier());
			userdoc.put("name", torg.getName());
			userdoc.put("NIF", torg.getNIF());
			userdoc.put("telephone", torg.getTelephone());
			userdoc.put("bankAccount", torg.getBankAccount());

			BasicDBObject update = new BasicDBObject("$set", userdoc);
			collection.update(org, update);

		} else {
			throw new OrgNotFoundException("Organization with identifier "
					+ torg.getIdentifier() + "does not exists.");
		}

	}

	public void updateUser(OrgUser user, String idOrg) throws UserNotFoundException, OrgNotFoundException {

		DBCollection collection = db.getCollection(OCOLLECTION);

		if (existsOrg(collection, idOrg)) {
			if (existsElement(collection, idOrg, user.getIdentifier(), "users")) {

				BasicDBObject query = new BasicDBObject("identifier", idOrg);
				query.put("users.identifier", user.getIdentifier());
				DBObject userdoc = new BasicDBObject();

				userdoc.put("users.$.identifier", user.getIdentifier());
				userdoc.put("users.$.name", user.getName());
				userdoc.put("users.$.email", user.getEmail());
				userdoc.put("users.$.password", user.getPassword());
				userdoc.put("users.$.telephone", user.getTelephone());

				BasicDBObject update = new BasicDBObject("$set", userdoc);
				collection.update(query, update);
			} else {
				throw new UserNotFoundException("User with identifier "
						+ user.getIdentifier() + " does not exists.");
			}
		} else {
			throw new OrgNotFoundException("Organization with identifier "
					+ idOrg + "does not exists.");
		}
	}
	
	public void updateTerminal(){
		
	}
	
	public void updateFlow(){
		
	}
	
	public boolean existsOrg(DBCollection collection, String id) {
		BasicDBObject query = new BasicDBObject("identifier", id);
		DBCursor cursor 	= collection.find(query);
		if(cursor.count()!=0) return true;
		else return false;
		
	}
	public boolean existsElement(DBCollection collection, String idOrg, String id, String type){
		BasicDBObject query = new BasicDBObject("identifier", idOrg);
		query.put("users.identifier", id);
		DBCursor cursor = collection.find(query);
		if(cursor.count()!=0) return true;
		else return false;
	}
}
