-- ============================================================
-- ALTER TABLE - Migração de thermography_v2.jdl para v6.jdl
-- ============================================================

-- ============================================================
-- ALTER TABLE: COMPANY
-- ============================================================
-- Adiciona campos de endereço detalhado e logo

ALTER TABLE company
ADD COLUMN logo LONGBLOB;

ALTER TABLE company
ADD COLUMN zip_code VARCHAR(255);

ALTER TABLE company
ADD COLUMN street VARCHAR(255);

ALTER TABLE company
ADD COLUMN number VARCHAR(255);

ALTER TABLE company
ADD COLUMN complement VARCHAR(255);

ALTER TABLE company
ADD COLUMN neighborhood VARCHAR(255);

ALTER TABLE company
ADD COLUMN city VARCHAR(255);

ALTER TABLE company
ADD COLUMN state VARCHAR(255);

ALTER TABLE company
ADD COLUMN country VARCHAR(255);

-- ============================================================
-- ALTER TABLE: THERMOGRAM
-- ============================================================
-- Adiciona campos de temperatura da óptica externa

ALTER TABLE thermogram
ADD COLUMN ext_optics_temp DOUBLE;

ALTER TABLE thermogram
ADD COLUMN ext_optics_trans DOUBLE;

-- ============================================================
-- ALTER TABLE: thermographic-record
-- ============================================================
-- Adiciona campos de condições ambientais e elétricas

ALTER TABLE thermographic-record
ADD COLUMN current DOUBLE;

ALTER TABLE thermographic-record
ADD COLUMN voltage DOUBLE;

ALTER TABLE thermographic-record
ADD COLUMN wind_speed DOUBLE;

ALTER TABLE thermographic-record
ADD COLUMN phase VARCHAR(255);

-- Auto-relacionamento: parentRecord (FK Optional)
-- Permite relacionar um ThermographicRecord com outro da mesma tabela
ALTER TABLE thermographic-record
ADD COLUMN parent_record_id UUID;

-- Adiciona índice para melhor performance nas queries
CREATE INDEX idx_thermographic-record_parent_record_id 
ON thermographic-record(parent_record_id);

-- Adiciona constraint de chave estrangeira
ALTER TABLE thermographic-record
ADD CONSTRAINT fk_thermographic-record_parent_record
FOREIGN KEY (parent_record_id) 
REFERENCES thermographic-record(id) 
ON DELETE SET NULL;


UPDATE public.thermographic-record
set type = 'ANOMALY_DETECTED'
where type = 'ANOMALY_INITIAL'

UPDATE public.thermographic-record
set type = 'ANOMALY_DETECTED'
where condition <> 'NORMAL'

-- Adicional: Enum constraint para 'phase' se necessário
-- ALTER TABLE thermographic-record
-- ADD CONSTRAINT check_phase CHECK (phase IN ('PHASE_A', 'PHASE_B', 'PHASE_C', 'NEUTRAL', 'GROUND', 'THREE_PHASE'));
