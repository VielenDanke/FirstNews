package kz.epam.news.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@Table(name = "COMMENTS")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_seq")
    @SequenceGenerator(name = "comment_seq", allocationSize = 1, sequenceName = "COMMENT_SEQ")
    @Column(name = "ID")
    private Long id;
    @Column(name = "NEWS_ID")
    private Long newsID;
    @Column(name = "AUTHOR_NAME")
    private String authorName;
    @Column(name = "DESCRIPTION")
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NEWS_ID", updatable = false, insertable = false)
    private News news;
}
