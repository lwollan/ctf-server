import Ajax from './Ajax';
import Immutable from 'immutable';

const delayPromise = timeout => new Promise(resolve => setTimeout(resolve, timeout));

Ajax.setUrlPrefix('https://ss-ctf-server.herokuapp.com/');

function getTeamFromLocalStorage() {
    try {
        return JSON.parse(localStorage.getItem('team'));
    } catch (e) {
        return null;
    }
}

class Datas {

    state = Immutable.fromJS({ team: getTeamFromLocalStorage() });
    subscribers = [];

    pollBoard() {
        this.getBoard()
            .then(() => delayPromise(1000))
            .then(() => this.pollBoard());
    }

    getBoard() {
        return Ajax.get('api/public/board/')
            .then(res => this.state = this.state.set('board', Immutable.fromJS(res)))
            .then(() => this.notify());
    }

    registerTeam(name) {
        this.state.isRegisteringTeam = true;

        return Ajax.post(`api/team/add/${name}`)
            .then(res => {
                const team = { name, ...res };

                localStorage.setItem('team', JSON.stringify(res));

                this.state = this.state
                    .set('isRegisteringTeam')
                    .set('team', Immutable.fromJS(team));
            })
            .then(() => this.notify());
    }

    notify() {
        this.subscribers.forEach(sub => sub(this.state));
    }

    logout = () => {
        this.state = this.state.set('team', null);
        this.notify();
    };

    subscribe(subFn) {
        subFn(this.state);

        this.subscribers.push(subFn);
    }

    unsubscribe(subFn) {
        const index = this.subscribers.indexOf(subFn);

        this.subscribers = this.subscribers.splice(index, 0);
    }
}

export default new Datas();