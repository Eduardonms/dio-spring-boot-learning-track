package dio.budgeting.domain;

import lombok.Getter;

@Getter
public class Transaction {
    private TransactionId id;
    private String description;
    private long amount;
    private Category category;

    public Transaction(String description, long amount, Category category) {
        this(new TransactionId(), description, amount, category);
    }

    public Transaction(TransactionId id, String description, long amount, Category category) {
        if (id == null) {
            throw new InvalidTransactionException("O identificador da transação é obrigatório.");
        }
        if (description == null || description.isBlank()) {
            throw new InvalidTransactionException("A descrição da transação não pode ficar em branco.");
        }
        if (amount <= 0) {
            throw new InvalidTransactionException("O valor da transação deve ser maior que zero.");
        }
        if (category == null) {
            throw new InvalidTransactionException("A categoria da transação é obrigatória.");
        }

        this.id = id;
        this.description = description;
        this.amount = amount;
        this.category = category;
    }
}
