package dev.fidgetcode.bot.city.view;

import dev.fidgetcode.bot.common.view.AbstractProfileController;
import dev.fidgetcode.bot.console.enumerable.EnumConfigurationKey;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class CityUpgradesLayoutController extends AbstractProfileController {

	@FXML
	private CheckBox checkBoxUpgradeFurnace,checkboxAcceptNewSurvivors;

	@FXML
	private TextField textFieldSirvivorsOffset;

	@FXML
	private void initialize() {

		checkBoxMappings.put(checkBoxUpgradeFurnace, EnumConfigurationKey.CITY_UPGRADE_FURNACE_BOOL);
		checkBoxMappings.put(checkboxAcceptNewSurvivors, EnumConfigurationKey.CITY_ACCEPT_NEW_SURVIVORS_BOOL);

		textFieldMappings.put(textFieldSirvivorsOffset,EnumConfigurationKey.CITY_ACCEPT_NEW_SURVIVORS_OFFSET_INT);


		initializeChangeEvents();
	}

}
