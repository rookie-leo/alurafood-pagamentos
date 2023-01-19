package br.com.alurafood.pagamentos.services;

import br.com.alurafood.pagamentos.dto.PagamentoDto;
import br.com.alurafood.pagamentos.http.PedidoClient;
import br.com.alurafood.pagamentos.models.Pagamento;
import br.com.alurafood.pagamentos.models.enums.Status;
import br.com.alurafood.pagamentos.repositories.PagamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PagamentoService {

    private PagamentoRepository repository;

    private ModelMapper modelMapper;

    private PedidoClient pedido;

    @Autowired
    public PagamentoService(PagamentoRepository repository, ModelMapper modelMapper, PedidoClient pedido) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.pedido = pedido;
    }

    public Page<PagamentoDto> obterTodos(Pageable paginacao) {
        return repository
                .findAll(paginacao)
                .map(p -> modelMapper
                        .map(p, PagamentoDto.class));
    }

    public PagamentoDto obterPorId(Long id) {
        Pagamento pagamento = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException()
        );

        return modelMapper.map(pagamento, PagamentoDto.class);
    }

    public PagamentoDto criarPagamento(PagamentoDto dto) {
        Pagamento pagamento = modelMapper.map(dto, Pagamento.class);
        pagamento.setStatus(Status.CRIADO);
        repository.save(pagamento);

        return modelMapper.map(pagamento, PagamentoDto.class);
    }

    public PagamentoDto atualizarPagamento(Long id, PagamentoDto dto) {
        Pagamento pagamento = modelMapper.map(dto, Pagamento.class);
        pagamento.setId(id);
        pagamento = repository.save(pagamento);
        return modelMapper.map(pagamento, PagamentoDto.class);
    }

    public void excluirPagamento(Long id) {
        repository.deleteById(id);
    }

    public void confirmarPgmt(Long id) {
        Pagamento pgmt = repository.findById(id).orElseThrow(() -> new EntityNotFoundException());

        pgmt.setStatus(Status.CONFIRMADO);
        repository.save(pgmt);
        pedido.atualizarPagamento(pgmt.getPedidoId());
    }

    public void alteraStatus(Long id) {
        Pagamento pgmt = repository.findById(id).orElseThrow(() -> new EntityNotFoundException());

        pgmt.setStatus(Status.CONFIRMADO_SEM_INTEGRACAO);
        repository.save(pgmt);
    }
}
