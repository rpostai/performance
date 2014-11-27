package com.rp.performance.domain.prova.execucao;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.rp.performance.domain.BaseEntity;
import com.rp.performance.domain.prova.Prova;
import com.rp.performance.services.GeradorHash;

@Entity
@Table(name = "execucao_prova", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"candidato_id", "prova_id", "voucher" }) })
public class ExecucaoProva extends BaseEntity {

	private static final String SALT = "94fyy4nfff4%##Fdujkjjfjfjsqee";

	@OneToOne
	@JoinColumn(name = "candidato_id")
	private Candidato candidato;

	@OneToOne
	@JoinColumn(name = "prova_id")
	private Prova prova;

	@Column(name = "voucher", length = 30, unique = true, updatable = false)
	private String voucher;

	@Column(name = "data_abertura", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAbertura;

	@Column(name = "data_validade", nullable = false)
	private Date dataValidade;

	@Column(name = "data_inicio")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInicio;

	@Column(name = "data_conclusao")
	private Date dataConclusao;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="execucaoProva")
	private Set<ExecucaoProvaResposta> respostas = new HashSet<ExecucaoProvaResposta>();
	
	public Candidato getCandidato() {
		return candidato;
	}

	public void setCandidato(Candidato candidato) {
		this.candidato = candidato;
	}

	public Prova getProva() {
		return prova;
	}

	public void setProva(Prova prova) {
		this.prova = prova;
	}

	public String getVoucher() {
		return voucher;
	}

	public Date getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(Date dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public Date getDataValidade() {
		return dataValidade;
	}

	public void setDataValidade(Date dataValidade) {
		this.dataValidade = dataValidade;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataConclusao() {
		return dataConclusao;
	}

	public void setDataConclusao(Date dataConclusao) {
		this.dataConclusao = dataConclusao;
	}

	@PrePersist
	public void prePersist() throws Exception {
		this.voucher = gerarCodigoVoucher(candidato, prova, dataAbertura);
	}

	private String gerarCodigoVoucher(Candidato candidato, Prova prova,
			Date dataAbertura) throws Exception {
		try {
			String aux = candidato.getCpf() + prova.getId()
					+ dataAbertura.getTime() + SALT;
			return GeradorHash.gerarHash(aux);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("Erro ao gerar voucher", e);
		}
	}

	public boolean isVoucherValido() throws Exception {
		String voucher = gerarCodigoVoucher(this.candidato, this.prova,
				this.dataAbertura);
		return this.voucher.equals(voucher);
	}
	
	public void addResposta(ExecucaoProvaResposta resposta) {
		resposta.setExecucaoProva(this);
		this.respostas.add(resposta);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((candidato == null) ? 0 : candidato.hashCode());
		result = prime * result + ((prova == null) ? 0 : prova.hashCode());
		result = prime * result + ((voucher == null) ? 0 : voucher.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExecucaoProva other = (ExecucaoProva) obj;
		if (candidato == null) {
			if (other.candidato != null)
				return false;
		} else if (!candidato.equals(other.candidato))
			return false;
		if (prova == null) {
			if (other.prova != null)
				return false;
		} else if (!prova.equals(other.prova))
			return false;
		if (voucher == null) {
			if (other.voucher != null)
				return false;
		} else if (!voucher.equals(other.voucher))
			return false;
		return true;
	}

}
