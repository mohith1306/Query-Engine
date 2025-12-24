# Query Engine (Java)

A small, educational “query engine” that parses simple commands and executes them against a minimal key-value storage engine.

This module demonstrates:
- **Query parsing** (`PUT`, `GET`, `DELETE`)
- **In-memory MemTable** (sorted map)
- **Write-Ahead Log (WAL)** for durability
- **Flush-to-disk** when the MemTable reaches a size threshold
- **Tombstones** for logical deletion

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
└─ (runtime)
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
- Keys/values are tokenized by whitespace (so values cannot contain spaces).
- Deletes are represented as a tombstone value.

## How it works (high level)

- `QueryParser` converts an input string into a `Query` object.
- `QueryExecutor` routes the query to the underlying `StorageEngine`.
- `StorageEngine`:
  - Appends every `PUT`/`DELETE` to `wal.log`
  - Stores recent updates in `MemTable`
  - When `MemTable` reaches its max size, it **flushes** entries into `dataFile.data` as `key=value` lines (append-only)
  - Reads:
    - check `MemTable` first
    - otherwise scan `dataFile.data` and return the *latest* value for the key

## Run

From this folder:

```bash
javac *.java
java Main
```

If you are in the repo root:

```bash
cd query-engine
javac *.java
java Main
```

## Output files

Running creates/updates these files in `query-engine/`:
- `wal.log` — write-ahead log
- `dataFile.data` — append-only storage file

If you want a clean run, delete `wal.log`, `dataFile.data`, and any `*.class` files before running again.
