package br.edu.ifba.segurancaApp.servicos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.edu.ifba.segurancaApp.dtos.OperacaoDTO;
import br.edu.ifba.segurancaApp.dtos.OperacaoForm;
import br.edu.ifba.segurancaApp.entidades.Conta;
import br.edu.ifba.segurancaApp.entidades.Operacao;
import br.edu.ifba.segurancaApp.entidades.TipoOperacao;
import br.edu.ifba.segurancaApp.entidades.Usuario;
import br.edu.ifba.segurancaApp.repositorios.ContaRepository;
import br.edu.ifba.segurancaApp.repositorios.OperacaoRepository;
import br.edu.ifba.segurancaApp.repositorios.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class OperacaoService { //TODO as operações devem enviar emails

    private OperacaoRepository operacaoRepository;
    private ContaRepository contaRepository;
    private UsuarioRepository usuarioRepository;

    public OperacaoService(OperacaoRepository operacaoRepository, ContaRepository contaRepository, UsuarioRepository usuarioRepository) {
        this.operacaoRepository = operacaoRepository;
        this.contaRepository = contaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public void realizarDeposito(OperacaoForm depositoForm, String username) {
        Usuario usuario = usuarioRepository.findByLogin(username);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        Conta conta = contaRepository.findByNumero(usuario.getId());
        if (conta == null) {
            throw new IllegalArgumentException("Conta não encontrada para o usuário");
        }

        conta.setSaldo(conta.getSaldo().add(depositoForm.valor()));
        contaRepository.save(conta);

        Operacao operacao = new Operacao();
        operacao.setConta(conta);
        operacao.setTipo(TipoOperacao.DEPOSITO);
        operacao.setValor(depositoForm.valor());
        operacao.setDataHora(LocalDateTime.now());

        operacaoRepository.save(operacao);
    }

    @Transactional
    public void realizarSaque(OperacaoForm saqueForm, String username) {
        Usuario usuario = usuarioRepository.findByLogin(username);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        Conta conta = contaRepository.findByNumero(usuario.getId());
        if (conta == null) {
            throw new IllegalArgumentException("Conta não encontrada para o usuário");
        }

        if (conta.getSaldo().compareTo(saqueForm.valor()) < 0) {
            throw new IllegalStateException("Saldo insuficiente para saque");
        }

        conta.setSaldo(conta.getSaldo().subtract(saqueForm.valor()));
        contaRepository.save(conta);

        Operacao operacao = new Operacao();
        operacao.setConta(conta);
        operacao.setTipo(TipoOperacao.SAQUE);
        operacao.setValor(saqueForm.valor());
        operacao.setDataHora(LocalDateTime.now());

        operacaoRepository.save(operacao);
    }

    @Transactional
    public void realizarPagamento(OperacaoForm pagamentoForm, String username) {
        Usuario usuario = usuarioRepository.findByLogin(username);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        Conta pagante = contaRepository.findByNumero(usuario.getId());
        if (pagante == null) {
            throw new IllegalArgumentException("Conta não encontrada para o usuário");
        }

        if (pagante.getSaldo().compareTo(pagamentoForm.valor()) < 0) {
            throw new IllegalStateException("Saldo insuficiente para pagamento");
        }

        Conta recipiendario = contaRepository.findByNumero(pagamentoForm.numeroConta());
        if (recipiendario == null) {
            throw new IllegalArgumentException("Conta não encontrada para o recebedor");
        }

        pagante.setSaldo(pagante.getSaldo().subtract(pagamentoForm.valor()));
        contaRepository.save(pagante);
        recipiendario.setSaldo(recipiendario.getSaldo().add(pagamentoForm.valor()));
        contaRepository.save(recipiendario);

        Operacao operacaoRecebedor = new Operacao();
        operacaoRecebedor.setConta(recipiendario);
        operacaoRecebedor.setTipo(TipoOperacao.RECEBIMENTO);
        operacaoRecebedor.setValor(pagamentoForm.valor());
        operacaoRecebedor.setDataHora(LocalDateTime.now());
        operacaoRecebedor.setDescricao(pagamentoForm.descricao());
        operacaoRepository.save(operacaoRecebedor);

        Operacao operacaoPagante = new Operacao();
        operacaoPagante.setConta(pagante);
        operacaoPagante.setTipo(TipoOperacao.PAGAMENTO);
        operacaoPagante.setValor(pagamentoForm.valor());
        operacaoPagante.setDataHora(LocalDateTime.now());
        operacaoPagante.setDescricao(pagamentoForm.descricao());

        operacaoRepository.save(operacaoPagante);
    }

    public List<OperacaoDTO> consultarExtrato(String username) {
        Usuario usuario = usuarioRepository.findByLogin(username);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        Conta conta = contaRepository.findByNumero(usuario.getId());
        if (conta == null) {
            throw new IllegalArgumentException("Conta não encontrada");
        }

        return conta.getOperacoes().stream()
                .map(OperacaoDTO::new)
                .collect(Collectors.toList());
    }

}
