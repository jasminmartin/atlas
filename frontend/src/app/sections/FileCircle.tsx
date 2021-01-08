import React, { Component } from 'react'

export default class FileCircle extends Component {
    render() {
        return (
            <button style={circleStyle}>
                <text>It's a circle!</text>
            </button>
        )
    }
}

const circleStyle = {
    width: window.innerHeight * 0.5,
    height: window.innerHeight * 0.5,
    borderRadius: window.innerWidth + window.innerHeight / 2,
    backgroundColor: "red",
    justifyContent: 'center',
    alignItems: 'center'
};

