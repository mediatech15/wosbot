package dev.fidgetcode.bot.profile.model;

import java.util.List;

import dev.fidgetcode.bot.ot.DTOProfiles;
import dev.fidgetcode.bot.serv.IProfileStatusChangeListener;

public interface IProfileModel {

	public List<DTOProfiles> getProfiles();

	public boolean addProfile(DTOProfiles profile);

	public boolean saveProfile(DTOProfiles profile);
	public boolean deleteProfile(DTOProfiles profile);

	public boolean bulkUpdateProfiles(DTOProfiles templateProfile);

	public void addProfileStatusChangeListerner(IProfileStatusChangeListener listener);

}
