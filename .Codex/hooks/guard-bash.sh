#!/usr/bin/env bash

input=$(cat)

has() { printf '%s' "$input" | grep -Eq "$1"; }

blocked=""
if has '(mvn|mvnw)' && has 'docker:push'; then
    # fabric8: mvn docker:push (в т.ч. через mise exec / ./mvnw)
    blocked="mvn docker:push"
elif has '(^|[^[:alnum:]_./-])docker[[:space:]]+push([[:space:]]|\\?"|$)'; then
    # docker CLI: docker push ...
    blocked="docker push"
elif has '(mvn|mvnw)[^"]*[[:space:]]deploy([[:space:]]|\\?"|$)'; then
    # публикация артефакта в удалённый maven-репозиторий
    blocked="mvn deploy"
fi

if [ -n "$blocked" ]; then
    echo 'Публикацию выполняет GitHub Actions. См. README.md.' >&2
    exit 2
fi
