# Annual Disney Villain Conference

It's time once again for the greatest villains across the disney universe to get together and share
in mediocre appetizers in a hotel ballroom in Cincinnati.

## Instructions

People are starting to line up outside the door, but we have lost the list of who should be allowed
in! Looks like we are going to gather information from those in line.

## Core Stories

### Start an attendees list

* We need to start somewhere, so lets use `attendees.json` to form our initial attendees list.
    * `attendees.json` represents the characters that are currently standing in line.
* After reading in the information make sure to store it as a variable that we can interact with
  later.

### Add the late arrivals

* Looks like some stragglers just showed up
* Take the new list from `lateArrivals.json` and add them to the attendees list created in the
  previous step.
* We should now have one List object that contains all the characters from `attendees.json`
  and `lateArrivals.json`.

### Remove the heroes

* Every year disney heroes try to sneak in. Luckily most of them aren't that smart and use their
  real names.
* We got an anonymous tip that `"Hercules"`, `"Merida"`, and `"Maui"` may be trying to sneak in.
  Search for these characters and if you find them remove them from the list of attendees.

### Test their values

* While that takes care of most of the heroes, we have an alignment test to see if they are really
  evil or not.
* The results of that test are stored in `alignmentTestResults.json`.
* Read in the results and update each character in our attendees list with a key `"alignment"` and
  their respective value of `"Good"` or `"Evil"`

### Remove any remaining heroes

* Go through the attendees list and remove anyone who has an `alignment` of `Good`

### Save the verified list

* Now that we have a list of verified villains let's save it to a `verifiedAttendees.json` file, so
  the bouncers know who to let in.
