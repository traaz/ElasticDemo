package com.example.demo;

import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ElasticFilterRepository {

    private final ElasticsearchOperations elasticsearchTemplate;

    public ElasticFilterRepository(ElasticsearchOperations elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    public List<User> searchByFilter(String name, String surname, Integer age) {
        // NativeQuery oluşturuluyor
        NativeQueryBuilder queryBuilder = NativeQuery.builder();

        // Bool sorgusu oluştur
        queryBuilder.withQuery(q -> q.bool(b -> {
            // Koşullu sorgu ekleme
            if (name != null && !name.isBlank()) {
                b.must(m -> m.matchPhrasePrefix(mp -> mp.field("userName").query(name)));
            }
            if (surname != null && !surname.isBlank()) {
                b.must(m -> m.matchPhrasePrefix(mp -> mp.field("userSurname").query(surname)));
            }
            if (age != null) {
                b.must(m -> m.term(t -> t.field("age").value(age)));
            }
            return b; // BoolQueryBuilder'ı döndür
        }));

        // Sorguyu oluştur
        NativeQuery query = queryBuilder.build();

        // Elasticsearch sorgusunu gönderiyoruz ve sonuçları alıyoruz
        return elasticsearchTemplate
                .search(query, User.class) // User sınıfı tipi belirleniyor
                .stream()
                .map(SearchHit::getContent) // Sonuçları alıyoruz
                .toList();
    }
}
