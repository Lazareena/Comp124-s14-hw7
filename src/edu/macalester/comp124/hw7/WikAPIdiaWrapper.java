package edu.macalester.comp124.hw7;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.io.FilenameUtils;
import org.wikapidia.conf.ConfigurationException;
import org.wikapidia.core.cmd.Env;
import org.wikapidia.core.cmd.EnvBuilder;
import org.wikapidia.core.dao.*;
import org.wikapidia.core.dao.sql.SimpleSqlDaoIterable;
import org.wikapidia.core.lang.Language;
import org.wikapidia.core.lang.LanguageSet;
import org.wikapidia.core.lang.LocalId;
import org.wikapidia.core.model.*;
import org.wikapidia.dao.load.PipelineLoader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Shilad wrote this wrapper around the WikAPIdia API for COMP 124.
 *
 * The design strives to be understandable to intro students, so parts of it may seem
 * awkward to experienced Java programmers.
 *
 * @author Shilad Sen
 */
public class WikAPIdiaWrapper {

    // The data directory for WikAPIdia. Change this so it is correct for your laptop
    // It should be the full path to either "wp-db-large" or "wp-db-small"
    private static final String DATA_DIRECTORY = "/Users/shilad/Documents/IntelliJ/comp-124-s14/hw7/wp-hw7";

    private static final int CONCEPT_ALGORITHM_ID = 1;

    private final Env env;
    private RawPageDao rpDao;
    private LocalPageDao lpDao;
    private LocalLinkDao llDao;
    private UniversalPageDao upDao;
    private LocalCategoryMemberDao cmDao;


    public WikAPIdiaWrapper() {
        this(DATA_DIRECTORY);
    }

    /**
     * Creates a new wrapper object with default configuration settings.
     *
     * baseDir should be the parent "wikAPIdia" directory containing the "db" directory.
     * You must have read / write permissions in this directory.
     */
    public WikAPIdiaWrapper(String baseDir) {
        try {
            File dbDir = new File(baseDir);
            System.err.println("Checking to see if " + dbDir.getAbsolutePath() + " exists...");
            if (!FilenameUtils.getBaseName(dbDir.getAbsolutePath()).equals("db")) {
                dbDir = new File(dbDir, "db");
            }
            if (!dbDir.isDirectory()) {
                System.err.println(
                        "\n\n!!!!!!!!!!!!!!ERROR. READ THIS MESSAGE!!!!!!!!!!!!!!!!!\n" +
                                "Database directory " + dbDir.getAbsolutePath() + " does not exist.\n" +
                                "Have you downloaded and extracted the database?\n" +
                                "Did you specify the correct DATA_DIRECTORY in WikAPIdiaWrapper?"
                );
                System.exit(1);
            }
            env = new EnvBuilder()
                    .setBaseDir(dbDir.getParent())
                    .build();
            this.rpDao = env.getConfigurator().get(RawPageDao.class);
            this.lpDao = env.getConfigurator().get(LocalPageDao.class);
            this.llDao = env.getConfigurator().get(LocalLinkDao.class);
            this.upDao = env.getConfigurator().get(UniversalPageDao.class);
            this.cmDao = env.getConfigurator().get(LocalCategoryMemberDao.class);
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return The list of installed languages.
     */
    public List<Language> getLanguages() {
        LanguageSet lset = env.getLanguages();
        return new ArrayList<Language>(lset.getLanguages());
    }

    /**
     * Returns a local page with a particular title.
     */
    public LocalPage getLocalPageByTitle(Language language, String title) {
        try {
            return lpDao.getByTitle(new Title(title, language), NameSpace.ARTICLE);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns a list of ALL the local pages in a particular language.
     * @param language
     * @return
     */
    public List<LocalPage> getLocalPages(Language language) {
        DaoFilter df = new DaoFilter()
                .setLanguages(language)
                .setRedirect(false)
                .setDisambig(false)
                .setNameSpaces(NameSpace.ARTICLE);
        try {
            return IteratorUtils.toList(lpDao.get(df).iterator());
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Return the first n page texts in a particular language
     * @param language
     * @param n
     * @return
     */
    public List<String> getPageTexts(Language language, int n) {
        DaoFilter df = new DaoFilter()
                .setLanguages(language)
                .setRedirect(false)
                .setDisambig(false)
                .setLimit(n)
                .setNameSpaces(NameSpace.ARTICLE);
        try {
            List<String> texts = new ArrayList<String>();
            SimpleSqlDaoIterable<RawPage> iterable = (SimpleSqlDaoIterable<RawPage>) rpDao.get(df);
            for (RawPage rp : iterable) {
                texts.add(rp.getBody());
                if (texts.size() >= n) {
                    break;
                }
            }
            iterable.close();
            return texts;
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns a list of the pages that represent the same concept in other languages.
     *
     * @param page
     * @return All pages that represent the same concept, INCLUDING the page passed as an argument.
     */
    public List<LocalPage> getInOtherLanguages(LocalPage page) {
        try {
            int conceptId = upDao.getUnivPageId(page, CONCEPT_ALGORITHM_ID);
            List<LocalPage> results = new ArrayList<LocalPage>();
            UniversalPage up = upDao.getById(conceptId, CONCEPT_ALGORITHM_ID);
            if (up == null) {
                return results;
            }
            for (LocalId lid : up.getLocalEntities()) {
                if (!lid.equals(page.toLocalId())) {
                    LocalPage lp = lpDao.getById(lid.getLanguage(), lid.getId());
                    if (lp != null) {
                        results.add(lp);
                    }
                }
            }
            results.add(page);
            return results;
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the wiki markup of a page with some text.
     *
     * @param page
     * @return
     */
    public String getPageText(LocalPage page) {
        try {
            return rpDao.getById(page.getLanguage(), page.getLocalId()).getBody();
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Load a set of languages into the h2 database.
     * THIS MUST BE CALLED BEFORE AN INSTANCE OF WIKIPADIA WRAPPER IS CREATED!
     */
    public static void main(String args[]) throws ClassNotFoundException, InterruptedException, ConfigurationException, IOException {
        PipelineLoader.main(new String[]{
                "-l", "simple,cy,sco,hi,is,bs",    // You can change this line to load different languages.
                "-f",
                "--base-dir", DATA_DIRECTORY,
                "-s", "fetchlinks:on",
                "-s", "download:on",
                "-s", "dumploader:on",
                "-s", "redirects:on",
                "-s", "concept:on",
        });
    }
}
