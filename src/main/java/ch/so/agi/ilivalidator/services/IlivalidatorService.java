package ch.so.agi.ilivalidator.services;

import javax.inject.Singleton;

import org.interlis2.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ehi.basics.settings.Settings;

@Singleton
public class IlivalidatorService {
    private static final Logger log = LoggerFactory.getLogger(IlivalidatorService.class);

    public boolean validate(String inputFileName, String logFileName) {
        log.info("Validating...");
        log.info(inputFileName);
        
        Settings settings = new Settings();
        settings.setValue(Validator.SETTING_ILIDIRS, Validator.SETTING_DEFAULT_ILIDIRS);
        settings.setValue(Validator.SETTING_LOGFILE, logFileName);

        boolean valid = Validator.runValidation(inputFileName, settings);
        return valid;
    }
}
