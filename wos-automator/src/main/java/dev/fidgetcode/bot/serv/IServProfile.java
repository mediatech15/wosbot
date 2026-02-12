package dev.fidgetcode.bot.serv;

import java.util.List;

import dev.fidgetcode.bot.ot.DTOProfiles;

public interface IServProfile {

	public List<DTOProfiles> getProfiles();

	public boolean addProfile(DTOProfiles profile);

	public boolean saveProfile(DTOProfiles profile);
	public boolean deleteProfile(DTOProfiles profile);

	public boolean bulkUpdateProfiles(DTOProfiles templateProfile);

	public void addProfileStatusChangeListerner(IProfileStatusChangeListener listener);

	public void addProfileDataChangeListener(IProfileDataChangeListener listener);
}
