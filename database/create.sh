##!/bin/bash
#export PGPASSWORD='postgres1'
#BASEDIR=$(dirname $0)
#DATABASE=virtual_stock
#psql -U postgres -f "$BASEDIR/dropdb.sql" &&
#createdb -U postgres $DATABASE &&
#psql -U postgres -d $DATABASE -f "$BASEDIR/schema.sql" &&
#psql -U postgres -d $DATABASE -f "$BASEDIR/data.sql" &&
#psql -U postgres -d $DATABASE -f "$BASEDIR/user.sql"

export PGPASSWORD='gdfA1-cg4Dag3cDcGDd5B-cF6aafG23e'
BASEDIR=$(dirname $0)
psql -h monorail.proxy.rlwy.net -U postgres -p 31217 -d railway -f "$BASEDIR/dropdb.sql" &&
psql -h monorail.proxy.rlwy.net -U postgres -p 31217 -d railway -f "$BASEDIR/schema.sql" &&
psql -h monorail.proxy.rlwy.net -U postgres -p 31217 -d railway -f "$BASEDIR/data.sql" &&
psql -h monorail.proxy.rlwy.net -U postgres -p 31217 -d railway -f "$BASEDIR/user.sql"