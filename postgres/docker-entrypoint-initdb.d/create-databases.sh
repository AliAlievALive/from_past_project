#!/bin/bash

set -e
set -u

function create_schema() {
	local schema=$1
	echo "  Creating schema '$schema'"
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
	    CREATE SCHEMA $schema;
EOSQL
}

function create_user() {
echo "Create user dev_past"
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
	    GRANT ALL PRIVILEGES ON DATABASE "past-local" TO dev_past;
EOSQL
echo "User dev_past created"
}

if [ -n "$POSTGRES_MULTIPLE_SCHEMAS" ]; then
	echo "Multiple schema creation requested: $POSTGRES_MULTIPLE_SCHEMAS"
	for db in $(echo $POSTGRES_MULTIPLE_SCHEMAS | tr ',' ' '); do
		create_schema $db
	done
	echo "Multiple schemas created"
fi
create_user