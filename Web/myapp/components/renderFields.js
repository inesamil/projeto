import React from 'react'

export default class RenderFields extends React.Component {
  constructor (props) {
    super(props)
    this.state = {
      action: this.props.action,
      [this.props.action.name]: {
        ...this.getHiddenInputs(this.props.action.fields)
      }
    }
    this.handleChange = this.handleChange.bind(this)
  }

  getHiddenInputs (fields) {
    const hiddenObjs = {}
    fields.forEach(field => {
      if (field.type === 'hidden') {
        hiddenObjs[field.name] = field.value
      }
    })
    return hiddenObjs
  }

  handleChange (event, obj) {
    const name = event.target.name
    const value = event.target.value || undefined
    this.setState(prevState => {
      const prevObj = prevState[obj] || {}
      prevObj[name] = value
      return {
        [obj]: prevObj
      }
    })
  }

  render () {
    return (
      <div>
        <p />
        {this.state.action.fields.map((field, idx) => {
          if (field.type === 'hidden') return
          const actionName = this.state.action.name
          const obj = this.state[actionName]
          const value = obj ? (obj[field.name] ? obj[field.name] : '') : ''
          return (
            <div key={idx}>
              {field.title}&nbsp;
              <input type={field.type} name={field.name} value={value} onChange={ev => this.handleChange(ev, actionName)} />
              <p />
            </div>
          )
        })}
        <div>
          <button onClick={this.props.onClose}>Cancel</button>
          &nbsp;
          <input type='submit' value={this.state.action.title} onClick={() => this.props.onSubmit(this.state[this.state.action.name])} />
        </div>
        <p />
      </div>
    )
  }
}
