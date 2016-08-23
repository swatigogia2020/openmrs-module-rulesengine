package org.openmrs.module.rulesengine.engine;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bahmni.extensions.BahmniExtensions;
import org.openmrs.module.rulesengine.domain.DosageRequest;
import org.openmrs.module.rulesengine.domain.Dose;
import org.openmrs.module.rulesengine.domain.RuleName;
import org.openmrs.module.rulesengine.rule.DosageRule;
import org.openmrs.module.rulesengine.util.RulesEngineProperties;
import org.openmrs.util.OpenmrsUtil;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Component
public class RulesEngineImpl implements RulesEngine {

    private static final String rulesEngineExtensionPath = "rulesengine" + File.separator + "rulesengineextension";

    private static Log log = LogFactory.getLog(RulesEngineImpl.class);

    @Override
    public Dose calculateDose(DosageRequest request) throws Exception {
        DosageRule rule = getRuleObject(request.getDosingRule());
        return rule.calculateDose(request);
    }

    public String[] getRuleNames() {
        String rules= RulesEngineProperties.getProperty("rules");
        String[] ruleNames=StringUtils.split(rules,'|');
        return ruleNames;
    }

    public String[] getRulesRegistered() throws Exception {
        List<DosageRule> dosageRules= getExtendedRules();
        dosageRules.addAll(getExistingRules());
        String[] ruleNames= new String[dosageRules.size()];
        for (int i=0;i<dosageRules.size();i++)
        {
            String currRuleName=getRuleName(dosageRules.get(i));
            if(ArrayUtils.contains(ruleNames,currRuleName))
                throw new Exception("Duplicate rule: "+currRuleName);
            ruleNames[i]=currRuleName;
        }

        return ruleNames;
    }

    public DosageRule getRuleObject(String ruleName) {
        List<DosageRule> dosageRules= getExtendedRules();
        dosageRules.addAll(getExistingRules());
        for (DosageRule rule: dosageRules) {
          if(getRuleName(rule).equalsIgnoreCase(ruleName)) return rule;
        }
        return null;
    }

    private String getRuleName(DosageRule rule)
    {
        RuleName annotation= rule.getClass().getAnnotation(RuleName.class);
        if(annotation!=null) return annotation.name();
        return rule.getClass().getSimpleName();
    }

    private List<DosageRule> getExtendedRules() {
        List<DosageRule> dosageRules=new ArrayList<>();
        File[] listOfFiles = getExtendedRuleFiles();
        if (listOfFiles != null) {
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    String name = listOfFiles[i].getName();
                    DosageRule rule = getGroovyObject(name);
                    dosageRules.add(rule);
                }
            }
        }
        return dosageRules;
    }

    private File[] getExtendedRuleFiles() {
        File folder = new File(getRulesEngineExtensionPath());
        File[] listOfFiles = folder.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String filename) {
                return filename.endsWith(".groovy");
            }
        });
        return listOfFiles;
    }

    private String getRulesEngineExtensionPath() {
        String ruleFilePath = OpenmrsUtil.getApplicationDataDirectory() +
                "bahmni_config" + File.separator + "openmrs" + File.separator +
                rulesEngineExtensionPath;
        return ruleFilePath;
    }

    private DosageRule getGroovyObject(String ruleFileName) {
        BahmniExtensions bahmniExtensions = new BahmniExtensions();
        return (DosageRule) bahmniExtensions.getExtension(rulesEngineExtensionPath, ruleFileName);
    }

    private List<DosageRule> getExistingRules() {
        List<DosageRule> dosageRules= new ArrayList<>();
        Reflections reflections=new Reflections("org.openmrs.module.rulesengine.rule");
        Set<Class<? extends DosageRule>> existingRules =
                reflections.getSubTypesOf(DosageRule.class);

        Iterator iter = existingRules.iterator();
        while (iter.hasNext()) {
            try {
                Class<DosageRule> dosageRuleCls = (Class<DosageRule>) iter.next();
                DosageRule rule = dosageRuleCls.newInstance();
                dosageRules.add(rule);
            }
            catch (Exception ex) {
                log.error("Error while creating existing rule object,"+ex.getMessage());
            }
        }
        return dosageRules;
    }

}
