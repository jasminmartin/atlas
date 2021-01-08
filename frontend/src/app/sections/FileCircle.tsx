import React, { Component } from 'react'

type CircleProps = {
    fileName: string

}

const FileCircle = ({ fileName }: CircleProps) => (
    <button style={circleStyle}>
        <text>{fileName}</text>
    </button>
)

export default FileCircle;

const circleStyle = {
    width: window.innerHeight * 0.5,
    height: window.innerHeight * 0.5,
    borderRadius: window.innerWidth + window.innerHeight / 2,
    backgroundColor: "red",
    justifyContent: 'center',
    alignItems: 'center'
};

