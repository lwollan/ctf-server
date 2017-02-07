import React from 'react';
import FoodSearch from './FoodSearch';

const App = React.createClass({
  getInitialState: function () {
    return {
      selectedFoods: [],
    };
  },
  render: function () {
    return (
      <div className='App'>
        <div>
          <FoodSearch/>
        </div>
      </div>
    );
  },
});

export default App;
