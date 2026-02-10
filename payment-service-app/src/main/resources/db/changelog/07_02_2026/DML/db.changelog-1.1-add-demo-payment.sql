-- liquibase formatted sql
-- changeset kalinin:1.1-insert-demo-payment
-- preConditions onFail:MARK_RAN
INSERT INTO payments (guid,
                      inquiry_ref_id,
                      amount,
                      currency,
                      transaction_ref_id,
                      status,
                      note,
                      created_at,
                      updated_at)
SELECT
    uuid_in(md5('payment-' || i || '-guid')::cstring),
    uuid_in(md5('payment-' || i || '-inquiry')::cstring),
    (50 + random() * 300)::numeric(5,2),
        (ARRAY['USD','EUR','GBP','CAD','JPY','CHF','AUD'])[(mod(i-1, 7) + 1)],
    uuid_in(md5('payment-' || i || '-transaction')::cstring),
    (ARRAY['RECEIVED','PENDING','DECLINED','APPROVED','NOT_SENT'])[(mod(i-1, 5) + 1)],
    'Payment ' || i || ' for testing',
    NOW() - (i || ' days')::interval,
    NOW() - (i || ' days')::interval
FROM generate_series(1, 15) AS i;