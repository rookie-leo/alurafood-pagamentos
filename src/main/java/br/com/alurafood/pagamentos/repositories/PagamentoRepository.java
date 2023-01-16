package br.com.alurafood.pagamentos.repositories;

import br.com.alurafood.pagamentos.models.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
