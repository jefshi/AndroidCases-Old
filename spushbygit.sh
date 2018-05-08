#!/bin/sh

function log() {
    echo -e '\n'$1'\n'
}

function presstoexit() {
    echo -e '\nplease press any key to exit.'
    read
}

git push -u origin master:master
log "git push successful !"

presstoexit
