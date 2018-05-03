package common;

import java.util.Map;
import java.util.TreeMap;

public class TableInfo {
    public static String[] tables = new String[]{
            "author",
            "author_organization",
            "journal",
            "keyword",
            "organization",
            "publication",
            "publication_author",
            "publication_keyword",
            "publication_redactor",
            "user",
            "request_authorship"
    };

    public static Map<String, String[]> table_fields = new TreeMap<>();

    static {
        table_fields.put(
                "author",
                new String[]{
                        "id",
                        "name",
                        "sex",
                        "date_of_birth",
                        "date_of_death",
                        "contacts"
                }
        );

        table_fields.put(
                "author_organization",
                new String[]{
                        "author",
                        "organization_id",
                        "work_time"
                }
        );

        table_fields.put(
                "journal",
                new String[]{
                        "id",
                        "name",
                        "subjects",
                        "publishing_house_id"
                }
        );

        table_fields.put(
                "keyword",
                new String[]{
                        "id",
                        "keyword"
                }
        );

        table_fields.put(
                "organization",
                new String[]{
                        "id",
                        "name",
                        "legal_address",
                        "contacts"
                }
        );

        table_fields.put(
                "publication",
                new String[]{
                        "id",
                        "type",
                        "udc_list",
                        "name",
                        "journal_id",
                        "publishing_house_id",
                        "organization_id",
                        "release_info",
                        "year_of_publishing"
                }
        );

        table_fields.put(
                "publication_author",
                new String[]{
                        "publication_id",
                        "author_id"
                }
        );

        table_fields.put(
                "publication_keyword",
                new String[]{
                        "publication_id",
                        "keyword_id"
                }
        );

        table_fields.put(
                "publication_redactor",
                new String[]{
                        "publication_id",
                        "redactor_id"
                }
        );

        table_fields.put(
                "request_authorship",
                new String[]{
                        "id",
                        "user_id"
                }
        );

        table_fields.put(
                "user",
                new String[]{
                        "id",
                        "role",
                        "login",
                        "pw",
                        "name",
                        "surname"
                }
        );
    }
}
