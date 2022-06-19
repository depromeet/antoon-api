package kr.co.antoon.vote.application;

import kr.co.antoon.error.dto.ErrorMessage;
import kr.co.antoon.error.exception.common.NotExistsException;
import kr.co.antoon.vote.domain.VoteItem;
import kr.co.antoon.vote.infrastructure.VoteItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VoteItemService {
    private final VoteItemRepository voteItemRepository;

    @Transactional(readOnly = true)
    public VoteItem findById(Long voteItemId) {
        return voteItemRepository.findById(voteItemId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_VOTE_ITEM));
    }
}
