import React from 'react'

export default ({onClick, name}) => (
  <span
    style={{cursor: 'pointer', textDecoration: 'underline'}}
    onClick={onClick}>{name}</span>
)
