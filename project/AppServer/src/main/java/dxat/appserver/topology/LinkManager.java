package dxat.appserver.topology;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import dxat.appserver.realtime.interfaces.ILinkEvents;
import dxat.appserver.realtime.pojos.ControllerEvent;
import dxat.appserver.topology.db.DbUpdate;
import dxat.appserver.topology.db.LinkTopologyDB;
import dxat.appserver.topology.exceptions.CannotOpenDataBaseException;
import dxat.appserver.topology.exceptions.LinkExistsException;
import dxat.appserver.topology.exceptions.LinkKeyBadFormatException;
import dxat.appserver.topology.exceptions.LinkNotFoundException;
import dxat.appserver.topology.exceptions.PortNotFoundException;
import dxat.appserver.topology.interfaces.ITopoLinkManager;
import dxat.appserver.topology.pojos.Link;
import dxat.appserver.topology.pojos.LinkCollection;

public class LinkManager implements ITopoLinkManager {
	private static LinkManager instance = null;

	public static LinkManager getInstance() {
		if (instance == null)
			instance = new LinkManager();
		return instance;
	}

	private Link getLinkFromJSON(String jsonStr) {
		return new Gson().fromJson(jsonStr, Link.class);
	}

	public List<DbUpdate> processEvent(ControllerEvent controllerEvent)
			throws JsonSyntaxException, PortNotFoundException,
			LinkNotFoundException, CannotOpenDataBaseException, LinkKeyBadFormatException {
		List<DbUpdate> updates = new ArrayList<DbUpdate>();
		String eventStr = controllerEvent.getEvent();
		
		if (eventStr.equals(ILinkEvents.LINK_UPDATED)
				|| eventStr.equals(ILinkEvents.SWITCH_UPDATED)
				|| eventStr.equals(ILinkEvents.PORT_UP)
				|| eventStr.equals(ILinkEvents.SWITCH_UPDATED)
				|| eventStr.equals(ILinkEvents.TUNEL_PORT_ADDED)) {
			Link link = getLinkFromJSON(controllerEvent.getObject());
			LinkTopologyDB linkTopologyDB = new LinkTopologyDB();
			try {
				linkTopologyDB.opendb();
				try {
					updates.addAll(linkTopologyDB.addLink(link));
				} catch (LinkExistsException e) {
					updates.addAll(linkTopologyDB.updateLink(link));
				}
			} finally {
				linkTopologyDB.closedb();
			}
		} else if (eventStr.equals(ILinkEvents.LINK_REMOVED)
				|| eventStr.equals(ILinkEvents.PORT_DOWN)
				|| eventStr.equals(ILinkEvents.SWITCH_REMOVED)
				|| eventStr.equals(ILinkEvents.TUNEL_PORT_REMOVED)) {
			Link link = getLinkFromJSON(controllerEvent.getObject());
			LinkTopologyDB linkTopologyDB = new LinkTopologyDB();
			try {
				linkTopologyDB.opendb();
				updates.addAll(linkTopologyDB.disableLink(link.getLinkKey()));
			} finally {
				linkTopologyDB.closedb();
			}
		} else if (eventStr.equals(ILinkEvents.LINKS_COLLECTION)) {
			LinkCollection linkCollection = new Gson().fromJson(
					controllerEvent.getObject(), LinkCollection.class);
			LinkTopologyDB linkDB = new LinkTopologyDB();
			linkDB.opendb();
			updates.addAll(linkDB.mergeCollection(linkCollection));
			linkDB.closedb();
		}
		return updates;
	}

	@Override
	public LinkCollection getLinks() {
		LinkTopologyDB linkTopologyDB = new LinkTopologyDB();
		LinkCollection linkCollection = null;
		try {
			linkTopologyDB.opendb();
			linkCollection = linkTopologyDB.getAllLinks();
		} catch (CannotOpenDataBaseException e) {
			e.printStackTrace();
		} finally {
			linkTopologyDB.closedb();
		}
		return linkCollection;
	}

	@Override
	public Link getLink(String srcPortId, String dstPortId) {
		LinkTopologyDB linkTopologyDB = new LinkTopologyDB();
		Link link = null;
		try {
			Link linkT = new Link();
			linkT.setDstPortId(dstPortId);
			linkT.setSrcPortId(srcPortId);
			linkTopologyDB.opendb();
			link = linkTopologyDB.getLink(linkT.getLinkKey());
		} catch (CannotOpenDataBaseException e) {
			e.printStackTrace();
		} catch (LinkNotFoundException e) {
			System.out.println(e.getMessage());
		} finally {
			linkTopologyDB.closedb();
		}
		return link;
	}

}