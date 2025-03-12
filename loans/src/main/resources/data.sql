INSERT INTO `loan` (
  `loan_id`, `mobile_number`, `loan_number`, `loan_type`,
  `total_loan`, `amount_paid`, `outstanding_amount`,
  `created_at`, `created_by`, `updated_at`, `updated_by`
) VALUES (
  1, '081234567890', 'LN-1001', 'Home Loan',
  50000000, 10000000, 40000000,
  '2025-03-08', 'LOAN_MS', NULL, NULL
);
