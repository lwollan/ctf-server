import React, { Component } from 'react';
import Datas from './Datas';
import Flag from './Flag';

export default class Flags extends Component {

    componentWillMount() {
        Datas.subscribe(this.unsub = state => this.setState({ flags: state.get('flags') }));

        Datas.getFlags();
    }

    componentWillUnmount() {
        Datas.unsubscribe(this.unsub);
    }

    render() {
        const { flags } = this.state;

        if (!flags) {
            return <div className="block">Loading flags</div>;
        }

        return (
            <div className="block flags">
                { flags
                    .sort((a, b) => a.get('flagName').localeCompare(b.get('flagName'), 'no', { numeric: true }))
                    .map(flag => <Flag key={ flag.get('flagId') } flag={ flag } />) }
            </div>
        );
    }
}
