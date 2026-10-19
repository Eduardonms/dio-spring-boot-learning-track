package dio.budgeting.application;

import dio.budgeting.application.input.PersistTransactionInput;
import dio.budgeting.domain.Category;
import dio.budgeting.domain.InvalidTransactionException;
import dio.budgeting.domain.Transaction;
import dio.budgeting.domain.TransactionRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PersistTransactionUseCaseTest {
    private final TransactionRepository transactionRepository = mock(TransactionRepository.class);
    private final PersistTransactionUseCase useCase = new PersistTransactionUseCase(transactionRepository);

    @Test
    void persistsValidTransactionAndReturnsAmountInReais() {
        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        var result = useCase.execute(new PersistTransactionInput("Compras do mercado", 1234, Category.GROCERIES));

        assertThat(result.id()).isNotBlank();
        assertThat(result.description()).isEqualTo("Compras do mercado");
        assertThat(result.category()).isEqualTo("GROCERIES");
        assertThat(result.value()).isEqualTo(12.34);
        verify(transactionRepository).save(argThat(transaction ->
                transaction.getDescription().equals("Compras do mercado")
                        && transaction.getAmount() == 1234
                        && transaction.getCategory() == Category.GROCERIES));
    }

    @Test
    void rejectsBlankDescriptionBeforeSaving() {
        assertThatThrownBy(() -> useCase.execute(new PersistTransactionInput(" ", 100, Category.GROCERIES)))
                .isInstanceOf(InvalidTransactionException.class)
                .hasMessageContaining("descrição");

        verifyNoInteractions(transactionRepository);
    }

    @Test
    void rejectsZeroOrNegativeAmountBeforeSaving() {
        assertThatThrownBy(() -> useCase.execute(new PersistTransactionInput("Compra", 0, Category.GROCERIES)))
                .isInstanceOf(InvalidTransactionException.class)
                .hasMessageContaining("maior que zero");
        assertThatThrownBy(() -> useCase.execute(new PersistTransactionInput("Compra", -1, Category.GROCERIES)))
                .isInstanceOf(InvalidTransactionException.class);

        verifyNoInteractions(transactionRepository);
    }

    @Test
    void rejectsMissingCategoryBeforeSaving() {
        assertThatThrownBy(() -> useCase.execute(new PersistTransactionInput("Compra", 100, null)))
                .isInstanceOf(InvalidTransactionException.class)
                .hasMessageContaining("categoria");

        verifyNoInteractions(transactionRepository);
    }
}
