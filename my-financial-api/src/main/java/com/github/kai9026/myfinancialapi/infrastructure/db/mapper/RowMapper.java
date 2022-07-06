package com.github.kai9026.myfinancialapi.infrastructure.db.mapper;

import io.vertx.mutiny.sqlclient.Row;

public interface RowMapper<T> {

  T convertToDomainObject(Row rowDb);
}
