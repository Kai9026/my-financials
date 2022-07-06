#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username finuser --dbname financialdb <<-EOSQL
  CREATE TYPE expense_type AS ENUM ('AGUA','GAS','LUZ');
  CREATE TABLE expenses (id bigserial NOT NULL, start_date date NOT NULL, end_date date NOT NULL, total numeric(5, 2) NOT NULL, billing_date date NOT NULL, "type" expense_type NULL, PRIMARY KEY (id));
EOSQL