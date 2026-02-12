package com.Chakradhar.GithubRepoSearch.repository;

import com.Chakradhar.GithubRepoSearch.entity.RepositoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GithubRepository extends JpaRepository<RepositoryEntity, Long> {
	
	@Query("""
			SELECT r FROM RepositoryEntity r
			WHERE (:language IS NULL OR r.language = :language)
			AND (:minStars IS NULL OR r.stars >= :minStars)
			ORDER BY
			CASE WHEN :sort = 'stars' THEN r.stars END DESC,
			CASE WHEN :sort = 'forks' THEN r.forks END DESC,
			CASE WHEN :sort = 'updated' THEN r.lastUpdated END DESC
			""")
			List<RepositoryEntity> findFiltered(
			        @Param("language") String language,
			        @Param("minStars") Integer minStars,
			        @Param("sort") String sort
			);
}
