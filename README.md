
# Query Engine (Java)

A small, educational query layer that parses commands and executes them against a minimal key-value storage engine.

## What it includes

- **Query parsing**: `PUT`, `GET`, `DELETE`
- **Query execution**: routes parsed queries to the storage engine
- **Storage engine**:
	- **MemTable** (in-memory, sorted map)
	- **Write-Ahead Log (WAL)** for `PUT`/`DELETE`
	- **Flush-to-disk** when MemTable reaches a max size
	- **Tombstones** for logical deletes

## Folder structure

```text
query-engine/
├─ Main.java
├─ QueryParser.java
├─ QueryExecutor.java
├─ StorageEngine.java
├─ MemTable.java
├─ WriteAheadLog.java
├─ Constants.java
└─ (generated at runtime)
	 ├─ wal.log
	 └─ dataFile.data
```

## Supported commands

```text
PUT <key> <value>
GET <key>
DELETE <key>
```

Notes:
- Input is split by whitespace, so `<value>` cannot contain spaces.

## How it works (high level)

- `QueryParser` converts an input string into a `Query` (type/key/value).
- `QueryExecutor` executes the query on `StorageEngine`.
- `StorageEngine` behavior:
	- `PUT`: append to `wal.log`, update `MemTable`, flush to disk if full
	- `DELETE`: append to `wal.log`, write tombstone to `MemTable`
	- `GET`: check `MemTable` first; otherwise scan `dataFile.data` and return the latest value for the key

## Run

From this folder:

```bash
javac *.java
java Main
```

## Clean run (optional)

Delete generated files and re-run:

```bash
del *.class
del wal.log
del dataFile.data
javac *.java
java Main
```

## Output files

- `wal.log`: write-ahead log of operations (`PUT`/`DELETE`)
- `dataFile.data`: append-only storage file of `key=value` entries

