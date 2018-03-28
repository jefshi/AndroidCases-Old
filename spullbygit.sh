#!/bin/sh

function log() {
    echo -e '\n'$1'\n'
}

function presstoexit() {
    echo -e '\nplease press any key to exit.'
    read
}

git pull
log 'git pull successful !'

presstoexit
