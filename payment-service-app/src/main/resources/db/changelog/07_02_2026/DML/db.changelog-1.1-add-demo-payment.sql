-- changeset kalinin:1.1-insert-demo-payment
-- comment: Insert demo payment row for development/testing
INSERT INTO payments (guid,
                      inquiry_ref_id,
                      amount,
                      currency,
                      transaction_ref_id,
                      status,
                      note,
                      created_at,
                      updated_at)
VALUES ('ac328a1a-1e60-4dd3-bee5-ed573d74c841',
        '607ed0ea-cb8a-4ff8-a694-1213c314e65c',
        99.99,
        'USD',
        'f113e373-b7b0-4f38-abf6-ccc3a89b8236',
        'RECEIVED',
        'Initial test payment',
        '2025-01-01 12:00:00+00',
        '2025-01-01 12:00:00+00');

-- rollback DELETE FROM payments WHERE guid = 'ac328a1a-1e60-4dd3-bee5-ed573d74c841';