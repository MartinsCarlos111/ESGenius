package br.com.fiap.esgenius.repository

import br.com.fiap.esgenius.model.InvestmentTip
import br.com.fiap.esgenius.model.TipCategory

object TipsRepository {
    fun getTips(): List<InvestmentTip> = listOf(
        InvestmentTip(
            id = 1,
            title = "Entenda seu perfil de risco",
            description = "Antes de investir, defina objetivos, horizonte e tolerância a perdas. Isso guia sua alocação e evita decisões impulsivas.",
            category = TipCategory.RISK
        ),
        InvestmentTip(
            id = 2,
            title = "Diversifique (setores, países e classes de ativos)",
            description = "Evite concentração. Combine renda fixa, ações, fundos imobiliários e, se fizer sentido, ETFs globais para reduzir volatilidade.",
            category = TipCategory.DIVERSIFICATION
        ),
        InvestmentTip(
            id = 3,
            title = "Avalie práticas de governança",
            description = "Procure empresas com conselho independente, transparência, política de dividendos clara e respeito a minoritários.",
            category = TipCategory.GOVERNANCE
        ),
        InvestmentTip(
            id = 4,
            title = "Evite greenwashing",
            description = "Não confie apenas em slogans. Leia relatórios de sustentabilidade e verifique métricas (escopos 1, 2 e 3 de emissões).",
            category = TipCategory.ENVIRONMENTAL,
            sources = listOf("https://www.unpri.org/")
        ),
        InvestmentTip(
            id = 5,
            title = "Considere fundos/ETFs com mandatos ESG",
            description = "Produtos com critérios ESG formais podem ser um atalho, mas compare taxa, histórico, metodologia e benchmark.",
            category = TipCategory.EDUCATION
        ),
        InvestmentTip(
            id = 6,
            title = "Vote e acompanhe assembleias",
            description = "O exercício de voto e o engajamento com a administração são pilares de governança responsável.",
            category = TipCategory.GOVERNANCE
        ),
        InvestmentTip(
            id = 7,
            title = "Gestão de riscos socioambientais",
            description = "Cheque políticas de saúde e segurança, relação com comunidades, gestão de resíduos, água e energia.",
            category = TipCategory.ENVIRONMENTAL
        ),
        InvestmentTip(
            id = 8,
            title = "Pense no longo prazo",
            description = "Critérios ESG costumam capturar riscos e oportunidades de longo prazo. Evite girar a carteira por ruídos de curto prazo.",
            category = TipCategory.EDUCATION
        ),
        InvestmentTip(
            id = 9,
            title = "Transparência regulatória",
            description = "Prefira empresas e fundos que reportam com padrões reconhecidos e cumprem regulações do mercado de capitais.",
            category = TipCategory.GOVERNANCE,
            sources = listOf("https://www.cvm.gov.br/")
        ),
        InvestmentTip(
            id = 10,
            title = "Tenha reserva de emergência",
            description = "Antes de qualquer investimento, mantenha 3–6 meses de despesas em ativo de alta liquidez e baixo risco.",
            category = TipCategory.RISK
        )
    )
}