package kr.co.antoon.subject.application;

import kr.co.antoon.subject.domain.Subject;
import kr.co.antoon.subject.domain.vo.SubjectType;
import kr.co.antoon.subject.infrastructure.SubjectRepository;
import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.NotExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;

    @Transactional(readOnly = true)
    public List<Subject> getSubjectsByTopUpper(SubjectType type) {
        return subjectRepository.findTop30ByTypeOrderByAmountDesc(type);
    }

    @Transactional(readOnly = true)
    public Subject findById(Long characterId) {
        return subjectRepository.findById(characterId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXISTS_CHARACTER));
    }
}
