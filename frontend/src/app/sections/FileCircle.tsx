import React, { Component, CSSProperties } from 'react'

type CircleProps = {
    fileName: string,
    leftDelta: number,
    rightDelta: number,
    bottomDelta: number,
    topDelta: number,
}

const FileCircle = ({ fileName, leftDelta, rightDelta, bottomDelta, topDelta }: CircleProps) => (
    <button style={circleStyle(leftDelta, rightDelta, bottomDelta, topDelta)}>
        <text>{fileName}</text>
    </button>
)

export default FileCircle;

const circleStyle = (leftDelta: number, rightDelta: number, bottomDelta: number, topDelta: number) => ({
    width: window.innerHeight * 0.5,
    height: window.innerHeight * 0.5,
    borderRadius: window.innerWidth + window.innerHeight / 2,
    backgroundColor: "red",
    position: "absolute",
    left: leftDelta,
    right: rightDelta,
    bottom: bottomDelta,
    top: topDelta
}) as const;

