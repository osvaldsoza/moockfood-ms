package br.com.osvaldsoza.ms_pagamentos.controller;

import br.com.osvaldsoza.ms_pagamentos.dto.PagamentoDto;
import br.com.osvaldsoza.ms_pagamentos.service.PagamentoService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @GetMapping
    public Page<PagamentoDto> obterTodos(@PageableDefault Pageable pageable) {
        return pagamentoService.obterTodos(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDto> obterPeloId(@PathVariable @NotNull Long id) {
        PagamentoDto pagamentoDTO = pagamentoService.obterPeloId(id);
        return ResponseEntity.ok(pagamentoDTO);
    }

    @PostMapping
    public ResponseEntity<PagamentoDto> criarPagamento(@RequestBody @Valid PagamentoDto pagamentoDTO,
                                                       UriComponentsBuilder uriComponentsBuilder) {
        PagamentoDto pagamentoDtoSalvo = pagamentoService.criarPagamento(pagamentoDTO);
        URI endereco = uriComponentsBuilder.path("/pagamentos/{id}").buildAndExpand(pagamentoDtoSalvo.getId()).toUri();
        return ResponseEntity.created(endereco).body(pagamentoDtoSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoDto> atualizarPagamento(@PathVariable @NotNull Long id, @RequestBody @Valid PagamentoDto pagamentoDTO) {
        PagamentoDto pagamentoDtoAtualizado = pagamentoService.atualizarPagamento(pagamentoDTO, id);
        return ResponseEntity.ok().body(pagamentoDtoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PagamentoDto> excluirPagamento(@PathVariable @NotNull Long id) {
        pagamentoService.excluirPagamento(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/confirmar")
    @CircuitBreaker(name = "atualizaPedido", fallbackMethod = "pagamentoAutoprizadoComintegracaoPendente")
    public void confirmaPagamento(@PathVariable Long id){
        pagamentoService.confirmaPagamento(id);
    }

    public void pagamentoAutoprizadoComintegracaoPendente(Long id, Exception exception){
        pagamentoService.alteraStatus(id);
    }
}
