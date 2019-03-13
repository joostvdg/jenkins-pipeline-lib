def call() {
  // assume name is env variable in declerative
  sh 'echo Hello $NAME'
}
