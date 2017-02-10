import React, { Component } from 'react';
import Datas from './Datas';

export default class ScoreBoard extends Component {

    state = {};

    componentDidMount() {
        Datas.subscribe(state => this.setState({ board: state.get('board') }));
    }

    render() {
        const { board } = this.state;

        if (!board) {
            return (
                <div>Loading board</div>
            );
        }

        return (
            <div>
                { board.get('title') }
                <table>
                    <thead>
                    <tr>
                        <th>Team</th>
                        <th>Score</th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        board && board.get('score').map(score => (
                            <tr key={ score.get('team') }>
                                <td>{ score.get('team') }</td>
                                <td>{ score.get('score') }</td>
                            </tr>
                        ))
                    }
                    </tbody>
                </table>
            </div>
        );
    }
}