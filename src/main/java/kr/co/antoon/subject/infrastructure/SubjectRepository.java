package kr.co.antoon.subject.infrastructure;

import kr.co.antoon.subject.domain.Subject;
import kr.co.antoon.subject.domain.vo.SubjectType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findTop30ByTypeOrderByAmountDesc(SubjectType type);
}
