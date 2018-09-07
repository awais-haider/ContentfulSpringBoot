package com.boot.contentful.service;

import com.boot.contentful.CDAEntryJsonMapper;
import com.contentful.java.cda.*;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
public class ContentfulService {
    private static final Logger logger = LoggerFactory.getLogger(ContentfulService.class);

    @Value("${contentful.api}")
    private String contentfulApi;

    @Autowired
    @Qualifier("contentfulDeliveryClient")
    private CDAClient contentfulDeliveryClient;

    @Autowired
    @Qualifier("contentfulPreviewClient")
    private CDAClient contentfulPreviewClient;

    @Autowired
    private ContentfulTypeIdResolver contentfulTypeIdResolver;

    @Cacheable("contentful")
    public String getTranslation(Locale locale, String subject) {
        CDAArray result = null;


        final CDASpace one =
                contentfulDeliveryClient
                        .fetchSpace();

        try {
            String translation_type_id = contentfulTypeIdResolver.getTranslationContentTypeId();
            if (usePreviewApi()) {
                result = contentfulPreviewClient.fetch(CDAEntry.class)
                        .where("content_type", translation_type_id)
                        .where("fields.subject", subject)
                        .where("limit", "1")
                        .all();

            } else {
                result = contentfulDeliveryClient.fetch(CDAEntry.class)
                        .where("content_type", translation_type_id)
                        .where("fields.subject", subject)
                        .where("limit", "1")
                        .all();
            }

            CDAEntry entry = (CDAEntry) result.items().get(0);
            Gson gson = new Gson();
            return gson.toJsonTree(entry.getField(ContentfulConstants.DICTIONARY_FIELD_ID.getValue()))
                    .getAsJsonObject().toString();
        } catch (Exception e) {
            logger.error("Unable to get translation for subject " + subject +
                    " and locale " + locale, e);
            return null;
        }
    }


  // @Cacheable(value = "ContentTypes", key = "#id")
  public String getContentTypeBasedOnId(Locale locale, String id) {
        CDAContentType cdaContentType = null;
        try {

            if (usePreviewApi()) {
                cdaContentType = contentfulPreviewClient.fetch(CDAContentType.class).where("locale", locale.toString()).one(id);

            } else {
                cdaContentType = contentfulDeliveryClient.fetch(CDAContentType.class).where("locale", locale.toString()).one(id);

            }

            if(null == cdaContentType){
                throw new Exception("No Data found");
            }
            Gson gson = new Gson();
            return gson.toJsonTree(cdaContentType)
                    .getAsJsonObject().toString();
        } catch (Exception e) {
            logger.error("Unable to get contenttype for id " + id +
                    " and locale "+ locale + " " + e.getMessage());
            return null;
        }
    }


    @Cacheable(value = "SingleEntry", key = "#id.concat(#locale)")
    @Transactional(readOnly = true)
    public String getSingleEntry(Locale locale, String id) {
        CDAEntry cdaEntry = null;
        //6EczfGnuHCIYGGwEwIqiq2
        try {

            if (usePreviewApi()) {

                cdaEntry =
                        contentfulPreviewClient
                                .fetch(CDAEntry.class)
                                .where("locale", locale.toString())
                                .one(id);

            } else {

                cdaEntry =
                        contentfulDeliveryClient
                                .fetch(CDAEntry.class)
                                .where("locale", locale.toString())
                                .one(id);
            }

            if (null == cdaEntry) {
                throw new Exception("No Data found");
            }

            Gson gson = new Gson();
            //CDAEntryJsonMapper CDAEntryJsonMapper = new CDAEntryJsonMapper(cdaEntry.id(), cdaEntry.rawFields(), cdaEntry.attrs(), cdaEntry.locale(), cdaEntry.contentType(), cdaEntry.type());
            CDAEntryJsonMapper cdaEntryJsonMapper = new CDAEntryJsonMapper(cdaEntry.id(), cdaEntry.rawFields());

            if (null != cdaEntryJsonMapper && null != cdaEntryJsonMapper.getRawFields()) {

                cdaEntryJsonMapper.getRawFields().remove("createdEntries");

            }

            return gson.toJsonTree(cdaEntryJsonMapper)
                    .getAsJsonObject().toString();


        } catch (Exception e) {
            logger.error("Unable to get contenttype for id " + id +
                    " and locale " + locale + " " + e.getMessage());
            return null;
        }
    }


    @CachePut(value = "SingleEntry", key = "#id.concat(#locale)")
    @Transactional(readOnly = true)
    public String getSingleEntryUpdate(Locale locale, String id) {
        CDAEntry cdaEntry = null;
        //6EczfGnuHCIYGGwEwIqiq2
        try {

            if (usePreviewApi()) {

                cdaEntry =
                        contentfulPreviewClient
                                .fetch(CDAEntry.class)
                                .where("locale", locale.toString())
                                .one(id);

            } else {

                cdaEntry =
                        contentfulDeliveryClient
                                .fetch(CDAEntry.class)
                                .where("locale", locale.toString())
                                .one(id);
            }

            if (null == cdaEntry) {
                throw new Exception("No Data found");
            }

            Gson gson = new Gson();
            //CDAEntryJsonMapper CDAEntryJsonMapper = new CDAEntryJsonMapper(cdaEntry.id(), cdaEntry.rawFields(), cdaEntry.attrs(), cdaEntry.locale(), cdaEntry.contentType(), cdaEntry.type());
            CDAEntryJsonMapper cdaEntryJsonMapper = new CDAEntryJsonMapper(cdaEntry.id(), cdaEntry.rawFields());

            if (null != cdaEntryJsonMapper && null != cdaEntryJsonMapper.getRawFields()) {

                cdaEntryJsonMapper.getRawFields().remove("createdEntries");
            }

            return gson.toJsonTree(cdaEntryJsonMapper)
                    .getAsJsonObject().toString();


        } catch (Exception e) {
            logger.error("Unable to get contenttype for id " + id +
                    " and locale " + locale + " " + e.getMessage());
            return null;
        }
    }

    @Cacheable("contentful")
    public String testSpace() {

        try {
            final CDASpace one =
                    contentfulDeliveryClient
                            .fetchSpace();

            Gson gson = new Gson();
            return gson.toJsonTree(one)
                    .getAsJsonObject().toString();
        } catch (Exception e) {
            logger.error("Unable to get translation for subject " +
                    " and locale " + e);
            return null;
        }
    }


    @Cacheable("contentful")
    public String testFetchContentType() {

        System.out.println("Here is");
        try {
            final CDAContentType cDAContentType =
                    contentfulDeliveryClient.fetch(CDAContentType.class).where("locale", "pa").one("1kUEViTN4EmGiEaaeC6ouY");
            Gson gson = new Gson();
            return gson.toJsonTree(cDAContentType)
                    .getAsJsonObject().toString();
        } catch (Exception e) {
            logger.error("Unable to get translation for subject " +
                    " and locale " + e);
            return null;
        }
    }

    @CacheEvict(value = "SingleEntry", key = "#id.concat(#locale)")
    public String testHook(String id, Locale locale) {

        return "WebHook is called";

    }


    private boolean usePreviewApi() {
        // use delivery
        return "preview".equals(contentfulApi);
    }
}
