-- liquibase formatted sql

-- changeset kalinin:1.0-create-payments

CREATE TABLE payments
(
    guid               UUID                     NOT NULL,
    inquiry_ref_id     UUID                     NOT NULL,
    amount             NUMERIC(5, 2)            NOT NULL,
    currency           VARCHAR(3)               NOT NULL,
    transaction_ref_id UUID,
    status             VARCHAR(255)             NOT NULL,
    note               TEXT,
    created_at         TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at         TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT pk_payments PRIMARY KEY (guid),
    CONSTRAINT uq_payments_guid UNIQUE (guid)
);

--rollback DROP TABLE payments;