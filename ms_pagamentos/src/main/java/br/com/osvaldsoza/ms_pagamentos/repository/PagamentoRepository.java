package br.com.osvaldsoza.ms_pagamentos.repository;

import br.com.osvaldsoza.ms_pagamentos.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento,Long> {
}
