package kz.epam.news.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NamedQuery(name = "News.findBySection", query = "select n From News n where n.section=:section")
@Entity
@NoArgsConstructor
@Data
@Table(name = "NEWS")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NEWS_SEQ")
    @SequenceGenerator(name = "NEWS_SEQ", allocationSize = 1, sequenceName = "news_seq")
    @Column(name = "ID")
    private Long id;
    @Column(name = "SECTION")
    private String section;
    @Column(name = "TOPIC")
    private String topic;
    @Column(name = "SHORT_DESCRIPTION")
    private String shortDescription;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "FILE_NAME")
    private String fileName;
    @OneToMany(
            mappedBy = "news",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    List<Comment> comments = new ArrayList<>();
}
