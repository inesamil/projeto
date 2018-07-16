import Bluebird from 'bluebird'

Bluebird.config({
  warnings: true,
  cancellation: true
})

export const makeCancellable = promise => Bluebird.resolve(promise)
