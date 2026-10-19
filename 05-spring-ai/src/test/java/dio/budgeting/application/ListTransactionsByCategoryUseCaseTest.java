package dio.budgeting.application;

import dio.budgeting.domain.Category;
import dio.budgeting.domain.Transaction;
import dio.budgeting.domain.TransactionRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ListTransactionsByCategoryUseCaseTest {
    private final TransactionRepository transactionRepository = mock(TransactionRepository.class);
    private final ListTransactionsByCategoryUseCase useCase =
            new ListTransactionsByCategoryUseCase(transactionRepository);

    @Test
    void returnsTransactionsForRequestedCategory() {
        when(transactionRepository.findAllByCategory(Category.PHARMA))
                .thenReturn(List.of(new Transaction("Farmácia", 2575, Category.PHARMA)));

        var results = useCase.execute(Category.PHARMA);

        assertThat(results).hasSize(1);
        assertThat(results.getFirst().description()).isEqualTo("Farmácia");
        assertThat(results.getFirst().category()).isEqualTo("PHARMA");
        assertThat(results.getFirst().value()).isEqualTo(25.75);
        verify(transactionRepository).findAllByCategory(Category.PHARMA);
    }
}
